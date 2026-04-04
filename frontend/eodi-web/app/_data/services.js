export const services = [
  {
    slug: "field-notes",
    name: "임장노트",
    href: "/field-notes",
    status: "메인 작업공간",
    available: false,
    summary: "방문 메모와 체크 포인트를 기록하고 공유하는 임장노트 서비스입니다.",
    description:
      "현장에서 본 내용을 쌓아두고 비교 후보와 연결해보는 중심 작업공간으로 확장하려고 합니다.",
    accent: "from-emerald-600 to-teal-500",
    ctaLabel: "작업공간 열기",
    platforms: ["웹", "모바일"],
    points: ["새 노트 만들기", "공유 가능한 노트", "임장 기록 정리"],
  },
  {
    slug: "search",
    name: "실거래 검색",
    href: "/search",
    status: "연결 기능",
    available: true,
    summary: "예산과 지역 기준으로 실거래가를 조회하고 노트 작성 전에 후보를 좁힙니다.",
    description:
      "노트 작성 전이나 비교 후보를 정리할 때 연결해서 쓰는 보조 진입 기능으로 유지합니다.",
    accent: "from-blue-600 to-cyan-500",
    ctaLabel: "검색 열기",
    platforms: ["웹", "모바일"],
    points: ["예산 기준 지역 추천", "매매 / 전세 / 월세 비교", "실거래 결과 연결"],
  },
  {
    slug: "data-visualization",
    name: "데이터 시각화",
    href: "/data-visualization",
    status: "준비 중",
    available: false,
    summary: "비교 후보를 거래량과 가격 흐름으로 시각적으로 정리하는 서비스입니다.",
    description:
      "임장노트에 쌓인 후보들을 더 입체적으로 비교할 수 있도록 차트 중심 화면을 준비 중입니다.",
    accent: "from-amber-500 to-orange-500",
    ctaLabel: "구성 보기",
    platforms: ["웹", "모바일"],
    points: ["거래량 추이 차트", "가격 흐름 비교", "후보 비교 보강"],
  },
];

export const serviceNavigation = [
  { label: "임장노트", href: "/field-notes" },
  { label: "실거래 검색", href: "/search" },
  { label: "데이터 시각화", href: "/data-visualization" },
];

export const servicesBySlug = Object.fromEntries(services.map((service) => [service.slug, service]));

