export const REGION_OPTIONS = [
  {
    value: "nowon-sanggye",
    label: "서울 노원구 상계동",
    title: "상계동",
    subtitle: "서울 노원구",
    meta: "서울 노원구 상계동",
    complexes: [
      {
        id: "sg-1",
        name: "상계주공 7단지",
        address: "서울시 노원구 동일로 1362",
        meta: "서울시 노원구 동일로 1362",
      },
      {
        id: "sg-2",
        name: "상계주공 10단지",
        address: "서울시 노원구 덕릉로 736",
        meta: "서울시 노원구 덕릉로 736",
      },
      {
        id: "sg-3",
        name: "보람아파트",
        address: "서울시 노원구 상계로 193",
        meta: "서울시 노원구 상계로 193",
      },
    ],
  },
  {
    value: "mapo-ahyeon",
    label: "서울 마포구 아현동",
    title: "아현동",
    subtitle: "서울 마포구",
    meta: "서울 마포구 아현동",
    complexes: [
      {
        id: "ah-1",
        name: "마포래미안푸르지오",
        address: "서울시 마포구 마포대로 195",
        meta: "서울시 마포구 마포대로 195",
      },
      {
        id: "ah-2",
        name: "공덕자이",
        address: "서울시 마포구 백범로 152",
        meta: "서울시 마포구 백범로 152",
      },
      {
        id: "ah-3",
        name: "마포자이더센트리지",
        address: "서울시 마포구 신촌로 270",
        meta: "서울시 마포구 신촌로 270",
      },
    ],
  },
  {
    value: "suwon-yeongtong",
    label: "경기 수원시 영통구",
    title: "영통구",
    subtitle: "경기 수원시",
    meta: "경기 수원시 영통구",
    complexes: [
      {
        id: "yw-1",
        name: "광교중흥S클래스",
        address: "경기도 수원시 영통구 광교호수로 300",
        meta: "경기도 수원시 영통구 광교호수로 300",
      },
      {
        id: "yw-2",
        name: "광교자연앤자이",
        address: "경기도 수원시 영통구 센트럴파크로 6",
        meta: "경기도 수원시 영통구 센트럴파크로 6",
      },
      {
        id: "yw-3",
        name: "e편한세상광교",
        address: "경기도 수원시 영통구 도청로 95",
        meta: "경기도 수원시 영통구 도청로 95",
      },
    ],
  },
];

export const RECOMMENDED_REGION_VALUES = ["nowon-sanggye", "mapo-ahyeon", "suwon-yeongtong"];

export const RECORD_TYPE_OPTIONS = [
  { value: "complex", label: "단지 기록", href: "/field-notes/new/complex", description: "" },
  { value: "region", label: "지역 기록", href: "/field-notes/new/region", description: "" },
];

export const STAR_SCORE_LABELS = {
  parking: {
    1: "매우 불편",
    2: "불편",
    3: "보통",
    4: "편리",
    5: "매우 편리",
  },
  sunlight: {
    1: "매우 부족",
    2: "부족",
    3: "보통",
    4: "좋음",
    5: "매우 좋음",
  },
  commercialArea: {
    1: "매우 부족",
    2: "부족",
    3: "보통",
    4: "편리",
    5: "매우 편리",
  },
  management: {
    1: "매우 아쉬움",
    2: "아쉬움",
    3: "보통",
    4: "좋음",
    5: "매우 좋음",
  },
  noise: {
    1: "매우 시끄러움",
    2: "시끄러움",
    3: "보통",
    4: "조용함",
    5: "매우 조용함",
  },
};

export const FACING_OPTIONS = [
  { label: "북서향", value: "북서향", row: 1, col: 1 },
  { label: "북향", value: "북향", row: 1, col: 2 },
  { label: "북동향", value: "북동향", row: 1, col: 3 },
  { label: "서향", value: "서향", row: 2, col: 1 },
  { label: "동향", value: "동향", row: 2, col: 3 },
  { label: "남서향", value: "남서향", row: 3, col: 1 },
  { label: "남향", value: "남향", row: 3, col: 2 },
  { label: "남동향", value: "남동향", row: 3, col: 3 },
];

export const FLATTENED_COMPLEXES = REGION_OPTIONS.flatMap((region) =>
  region.complexes.map((complex) => ({
    ...complex,
    regionValue: region.value,
    regionLabel: region.meta ?? region.label,
  }))
);

export function mapRegionToSheetItem(region) {
  return {
    key: region.value,
    title: region.title ?? region.label,
    subtitle: region.subtitle ?? "",
    meta: region.meta ?? region.label,
    raw: region,
  };
}

export function mapComplexToSheetItem(complex) {
  return {
    key: complex.id,
    title: complex.name,
    subtitle: complex.address,
    meta: complex.regionLabel || complex.meta || "",
    raw: complex,
  };
}
