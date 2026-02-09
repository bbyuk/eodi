package com.bb.eodi.legaldong.presentation.mapper;

import com.bb.eodi.legaldong.application.result.LegalDongFindResult;
import com.bb.eodi.legaldong.presentation.dto.response.LegalDongFindResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 법정동 조회 API 응답 DTO Mapper
 */
@Mapper(componentModel = "spring")
public interface LegalDongFindResponseMapper {

    default LegalDongFindResponse toResponse(List<LegalDongFindResult> results) {
        return new LegalDongFindResponse(toItems(results));
    }

    List<LegalDongFindResponse.Item> toItems(List<LegalDongFindResult> results);
    LegalDongFindResponse.Item toItem(LegalDongFindResult result);
}
