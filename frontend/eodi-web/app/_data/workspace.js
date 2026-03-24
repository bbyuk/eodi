export const workspaceSummary = [
  {
    label: "최근 임장노트",
    value: "6개",
    helper: "어제 마지막 수정",
  },
  {
    label: "관심 지역",
    value: "4곳",
    helper: "서울 동북권 중심",
  },
  {
    label: "비교 후보",
    value: "3개",
    helper: "가격과 동선 비교 중",
  },
];

export const recentFieldNotes = [
  {
    id: "note-1",
    title: "상계동 구축 아파트 1차 임장",
    region: "서울 노원구 상계동",
    updatedAt: "어제 20:30",
    status: "작성 중",
    href: "/field-notes",
  },
  {
    id: "note-2",
    title: "광교 신도시 가족 실거주 후보",
    region: "경기 수원시 영통구",
    updatedAt: "3월 22일",
    status: "공유됨",
    href: "/field-notes",
  },
  {
    id: "note-3",
    title: "마포 소형 평형 출퇴근 검토",
    region: "서울 마포구 아현동",
    updatedAt: "3월 20일",
    status: "초안",
    href: "/field-notes",
  },
];

export const favoriteRegions = [
  {
    id: "fav-1",
    name: "서울 노원구",
    summary: "예산 범위 후보가 많고 실거래 확인 빈도가 높습니다.",
    href: "/search",
  },
  {
    id: "fav-2",
    name: "경기 수원시 영통구",
    summary: "실거주 후보와 학군 확인을 함께 보고 있습니다.",
    href: "/search",
  },
  {
    id: "fav-3",
    name: "서울 마포구",
    summary: "출퇴근 거리와 소형 평형 매물 흐름을 보고 있습니다.",
    href: "/search",
  },
];

export const recentTransactions = [
  {
    id: "deal-1",
    name: "상계주공 7단지",
    meta: "매매 · 59㎡ · 7층",
    price: "6억 2,000",
    viewedAt: "방금 확인",
  },
  {
    id: "deal-2",
    name: "광교중흥S클래스",
    meta: "전세 · 84㎡ · 18층",
    price: "5억 8,000",
    viewedAt: "오늘 오전",
  },
  {
    id: "deal-3",
    name: "마포래미안푸르지오",
    meta: "월세 · 59㎡ · 12층",
    price: "2억 / 120",
    viewedAt: "어제",
  },
];

export const comparisonCandidates = [
  {
    id: "cmp-1",
    name: "상계주공 7단지 vs 10단지",
    summary: "예산은 비슷하지만 역 접근성과 단지 분위기를 더 봐야 합니다.",
    href: "/data-visualization",
    tags: ["가격", "동선", "단지 규모"],
  },
  {
    id: "cmp-2",
    name: "광교 자연앤자이 vs 중흥S클래스",
    summary: "거래량과 전세 회전율을 함께 보고 판단하려는 후보입니다.",
    href: "/data-visualization",
    tags: ["거래량", "전세", "학군"],
  },
];

export const noteTemplates = [
  {
    id: "tpl-1",
    title: "아파트 임장 기본 템플릿",
    description: "단지, 동선, 소음, 관리 상태를 빠르게 기록하는 기본 양식입니다.",
  },
  {
    id: "tpl-2",
    title: "동네 비교 템플릿",
    description: "두 지역 이상을 비교할 때 장단점을 한 번에 정리하는 양식입니다.",
  },
  {
    id: "tpl-3",
    title: "공유용 요약 템플릿",
    description: "가족이나 동행자에게 보내기 좋은 정리형 노트 양식입니다.",
  },
];

