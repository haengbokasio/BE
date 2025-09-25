package com.goorm.haengbokasio.service;

import com.goorm.haengbokasio.dto.MentorRegisterDto;
import com.goorm.haengbokasio.entity.Mentor;
import com.goorm.haengbokasio.repository.MentorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MentorService {

    private final MentorRepository mentorRepository;

    @Transactional
    public Long registerMentor(MentorRegisterDto dto) {

        // 카카오 ID로 기존 멘토가 있는지 조회
        Optional<Mentor> existingMentor = mentorRepository.findByKakaoId(dto.getKakaoId());
        Mentor mentorToSave;

        if (existingMentor.isPresent()) {
            // 기존 멘토가 있다면, 업데이트
            Mentor mentor = existingMentor.get();

            mentor.setPhoneNumber(dto.getPhoneNumber());
            mentor.setBusinessType(dto.getBusinessType());
            mentor.setBusinessDetail(dto.getBusinessDetail());
            mentor.setOperationPeriod(dto.getOperationPeriod());
            mentor.setMonthAvgRevenue(dto.getMonthAvgRevenue());
            mentor.setWeekAvgDailyRevenue(dto.getWeekAvgDailyRevenue());
            mentor.setBusinessAddress(dto.getBusinessAddress());
            mentor.setMainProductService(dto.getMainProductService());
            mentor.setTargetCustomer(dto.getTargetCustomer());
            mentor.setCustomerAcquisitionMethod(dto.getCustomerAcquisitionMethod());
            mentor.setMarketingMethod(dto.getMarketingMethod());
            mentor.setOperationMethod(dto.getOperationMethod());
            mentor.setSupplySource(dto.getSupplySource());

            mentorToSave = mentor;

        } else {
            mentorToSave = dto.toEntity();
            mentorRepository.save(mentorToSave);
        }

        return mentorToSave.getId();
    }
}