package com.bb.eodi.deal.application.port;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.entity.RealEstateSell;

/**
 * 부동산 플랫폼 URL 생성 port
 */
public interface RealEstatePlatformUrlGeneratePort {
    String generate(RealEstateSell realEstateSell);
    String generate(RealEstateLease realEstateLease);
}
