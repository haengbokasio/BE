package com.goorm.haengbokasio.service;

import com.goorm.haengbokasio.dto.MentiRegisterDto;
import com.goorm.haengbokasio.entity.Menti;
import com.goorm.haengbokasio.repository.MentiRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MentiService {

    private final MentiRepository mentiRepository;

    @Transactional
    public Long registerMentor(MentiRegisterDto dto) {

        // 카카오 ID로 기존 멘토가 있는지 조회
        Optional<Menti> existingMentor = mentiRepository.findByKakaoId(dto.getKakaoId());
        Menti mentiToSave;

        if (existingMentor.isPresent()) {
            // 기존 멘토가 있다면, 업데이트
            Menti menti = existingMentor.get();

            menti.setPhoneNumber(dto.getPhoneNumber());
            menti.setBusinessType(dto.getBusinessType());
            menti.setBusinessDetail(dto.getBusinessDetail());
            menti.setOperationPeriod(dto.getOperationPeriod());
            menti.setMonthAvgRevenue(dto.getMonthAvgRevenue());
            menti.setWeekAvgDailyRevenue(dto.getWeekAvgDailyRevenue());
            menti.setBusinessAddress(dto.getBusinessAddress());
            menti.setMainProductService(dto.getMainProductService());
            menti.setTargetCustomer(dto.getTargetCustomer());
            menti.setCustomerAcquisitionMethod(dto.getCustomerAcquisitionMethod());
            menti.setMarketingMethod(dto.getMarketingMethod());
            menti.setOperationMethod(dto.getOperationMethod());
            menti.setSupplySource(dto.getSupplySource());

            mentiToSave = menti;

        } else {
            mentiToSave = dto.toEntity();
            mentiRepository.save(mentiToSave);
        }

        return mentiToSave.getId();
    }

    public List<Menti> getAllMentis() {
        return mentiRepository.findAll();
    }
}