package com.bb.eodi.finance.presentation.mapper;

import com.bb.eodi.finance.application.input.RegulatingAreaRegisterInput;
import com.bb.eodi.finance.application.result.RegulatingAreaRegisterResult;
import com.bb.eodi.finance.presentation.dto.request.RegulatingAreaRegisterRequest;
import com.bb.eodi.finance.presentation.dto.response.RegulatingAreaRegisterResponse;
import org.mapstruct.Mapper;

/**
 * 부동산 규제지역 등록 API request parameter <-> input / result <-> response Mapper
 */
@Mapper(componentModel = "spring")
public interface RegulatingAreaRegisterMapper {
    RegulatingAreaRegisterResponse toResponse(RegulatingAreaRegisterResult result);
    RegulatingAreaRegisterInput toInput(RegulatingAreaRegisterRequest requestParameter);

}
