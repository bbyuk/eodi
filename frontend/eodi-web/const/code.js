import {
  BuildingOffice2Icon,
  BuildingOfficeIcon,
  HomeIcon,
  HomeModernIcon,
} from "@heroicons/react/24/outline";

export const definedHousingType = {
  AP: {
    name: "아파트",
    param: true,
    order: 0,
    icon: <BuildingOffice2Icon />,
  },
  OF: { name: "오피스텔", param: true, order: 1, icon: <BuildingOfficeIcon /> },
  MH: {
    name: "연립/다세대 주택",
    param: false,
    order: 2,
    icon: <HomeModernIcon />,
  },
  DT: { name: "단독 주택", param: false, order: 3, icon: <HomeIcon /> },
  PR: { name: "분양권", param: false },
  OR: { name: "입주권", param: false },
  MU: { name: "다가구 주택", param: false },
};
