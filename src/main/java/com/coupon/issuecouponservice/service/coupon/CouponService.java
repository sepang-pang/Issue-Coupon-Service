package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import com.coupon.issuecouponservice.domain.coupon.UserCoupon;
import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.coupon.CouponCreationParam;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.dto.request.coupon.CouponModificationParam;
import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.dto.response.coupon.CouponOneForm;
import com.coupon.issuecouponservice.repository.coupon.CouponRepository;
import com.coupon.issuecouponservice.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponQueryService userCouponQueryService;
    private final ImageService imageService;
    private final CouponScheduler couponScheduler;

    // 쿠폰 생성
    public void createCoupon(CouponCreationParam param, MultipartFile file) throws IOException {
        // 쿠폰 이름 중복 검증
        checkForDuplicateCouponName(param.getCouponName());

        // 쿠폰 마감일자 최신순으로 조회
        List<Coupon> coupons = couponRepository.findAllByOrderByClosedAtDesc();

        // 쿠폰 생성
        Coupon coupon = Coupon.CreateCoupon(param, coupons);

        // 쿠폰 이미지 업로드
        uploadImage(file, coupon);

        // 쿠폰 저장
        couponRepository.save(coupon);

        // 스케줄 등록
        couponScheduler.scheduleCouponStatusChange(coupon);
    }


    // 쿠폰 전체 조회
    @Transactional(readOnly = true)
    public List<CouponForm> readAllCoupons() {
        List<Coupon> findCoupons = couponRepository.findAllCoupons();

        return findCoupons.stream()
                .map(CouponForm::new)
                .collect(Collectors.toList());
    }

    // 진행 중 쿠폰 조회
    @Transactional(readOnly = true)
    public CouponOneForm readActiveCoupon() {
        Coupon findCoupon = couponRepository.findActiveCoupon().orElse(null);
        return findCoupon != null ? new CouponOneForm(findCoupon) : null;
    }

    // 예정된 쿠폰 전체 조히
    public Page<CouponForm> readAllOpenCoupons(Pageable pageable) {
        Page<Coupon> findCoupons = couponRepository.findOpenCoupons(pageable);

        return findCoupons.map(CouponForm::new);
    }

    // 종료된 쿠폰 전체 조회
    @Transactional(readOnly = true)
    public Page<CouponForm> readAllClosedCoupons(Pageable pageable) {
        // 쿠폰 목록 조회
        Page<Coupon> findCoupons = couponRepository.findClosedCoupons(pageable);

        return findCoupons.map(CouponForm::new);
    }

    // 쿠폰 수정
    public void modifyCoupon(Long couponId, CouponModificationParam param) {
        // 쿠폰 이름 중복 검증
        checkForDuplicateCouponName(param.getCouponName());

        // 쿠폰 조회
        Coupon findCoupon = getCoupon(couponId);

        // 쿠폰 수정
        findCoupon.modifyCoupon(param);
    }

    // 쿠폰 삭제
    public void deleteCoupon(Long couponId) {
        // 쿠폰 조회
        Coupon findCoupon = getCoupon(couponId);

        // 쿠폰 삭제
        findCoupon.deleteCoupon();
    }

    // 쿠폰 상세 조회
    @Transactional(readOnly = true)
    public CouponOneForm selectCoupon(Long couponId) {
        // 쿠폰 조회
        Coupon coupon = getCoupon(couponId);

        // 쿠폰 반환
        return new CouponOneForm(coupon);
    }

    // 쿠폰 발급
    public void issueCoupon(CouponIssueParam param, User user) {
        // 쿠폰 조회
        Coupon coupon = getCoupon(param.getCouponId());

        // 쿠폰 발급
        UserCoupon userCoupon = UserCoupon.CreateUserCoupon(coupon, user);

        userCouponQueryService.saveUserCoupon(userCoupon);
    }

    // 사용자 쿠폰 전체 조회
    @Transactional(readOnly = true)
    public Page<CouponForm> readAllUserCoupons(User user, Pageable pageable) {
        // 쿠폰 목록 조회
        Page<UserCoupon> findUserCoupons = userCouponQueryService.getUserCoupons(user.getId(), pageable);

        // 쿠폰 반환
        return findUserCoupons.map(uc -> new CouponForm(uc.getCoupon(), uc.getCreatedAt()));
    }


    // 쿠폰 조회 메서드
    private Coupon getCoupon(Long couponId) {
        return couponRepository.findOneCouponByCouponId(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    }

    // 쿠폰 검증 메서드
    private void checkForDuplicateCouponName(String couponName) {
        boolean exists = couponRepository.existsByCouponName(couponName);

        if (exists) {
            throw new IllegalArgumentException("이미 존재하는 쿠폰명입니다.");
        }
    }

    // 쿠폰 이미지 업로드 메서드
    private void uploadImage(MultipartFile file, Coupon coupon) throws IOException {
        if (file != null && !file.isEmpty()) {
            String couponFile = imageService.upload(file, "coupon " + coupon.getId());

            coupon.updateCouponImage(couponFile);
        }
    }

}
