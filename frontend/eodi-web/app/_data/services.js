export const services = [
  {
    slug: "search",
    name: "실거래 검색",
    href: "/search",
    status: "LIVE",
    available: true,
    summary: "예산과 지역 기준으로 매매, 전세, 월세 실거래가를 바로 조회합니다.",
    description:
      "보유 현금과 주거 유형을 입력하면 최근 실거래 흐름이 있는 지역과 조건별 결과를 이어서 확인할 수 있습니다.",
    accent: "from-blue-600 to-cyan-500",
    ctaLabel: "바로 시작",
    platforms: ["웹", "모바일"],
    points: ["예산 기준 지역 추천", "매매 / 전세 / 월세 비교", "실거래 결과 카드 탐색"],
  },
  {
    slug: "field-notes",
    name: "임장노트",
    href: "/field-notes",
    status: "준비 중",
    available: false,
    summary: "현장에서 본 단지와 동네를 기록하고, 다른 사람과 공유하는 노트 서비스입니다.",
    description:
      "방문 메모, 사진, 체크리스트를 한 번에 남기고 링크로 공유할 수 있는 흐름을 목표로 준비 중입니다.",
    accent: "from-emerald-600 to-teal-500",
    ctaLabel: "구성 보기",
    platforms: ["웹", "모바일"],
    points: ["현장 메모 정리", "공유 가능한 노트", "체크리스트 기반 기록"],
  },
  {
    slug: "data-visualization",
    name: "데이터 시각화",
    href: "/data-visualization",
    status: "준비 중",
    available: false,
    summary: "단지별 하락장 거래량, 가격 흐름 같은 지표를 보기 쉽게 시각화합니다.",
    description:
      "검색에서 찾은 지역이나 단지를 더 깊게 비교할 수 있도록 거래량과 가격 변화를 차트 중심으로 보여줄 예정입니다.",
    accent: "from-amber-500 to-orange-500",
    ctaLabel: "구성 보기",
    platforms: ["웹", "모바일"],
    points: ["거래량 추이 차트", "가격 흐름 비교", "단지별 지표 탐색"],
  },
];

export const serviceNavigation = services.map((service) => ({
  label: service.name,
  href: service.href,
}));

export const serviceFlow = [
  {
    id: "01",
    title: "실거래 검색",
    description: "예산과 지역 조건으로 먼저 후보를 좁힙니다.",
  },
  {
    id: "02",
    title: "임장노트",
    description: "직접 본 내용과 체크 포인트를 기록하고 공유합니다.",
  },
  {
    id: "03",
    title: "데이터 시각화",
    description: "거래량과 가격 흐름을 차트로 비교하며 판단을 보강합니다.",
  },
];

export const primaryService = services[0];

export const secondaryServices = services.slice(1);

export const servicesBySlug = Object.fromEntries(services.map((service) => [service.slug, service]));

