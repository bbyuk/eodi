import { formatWon } from "@/app/search/_util/util";

const NET_LEASABLE_AREA_OPTION = [33, 66, 99, 132, 165, 198, 231];

const toMinFilterParam = (key) => {
  return `min${key.charAt(0).toUpperCase() + key.slice(1)}`;
};

const toMaxFilterParam = (key) => {
  return `max${key.charAt(0).toUpperCase() + key.slice(1)}`;
};

export const createInitialFilters = () => ({
  sell: {
    price: {
      key: "price",
      label: "가격",
      dirtyChecker: (current, initial) => {
        if (!initial) return;

        return (
          current.enable !== initial.enable ||
          current.enableMin !== initial.enableMin ||
          current.enableMax !== initial.enableMax ||
          current.minValue !== initial.minValue ||
          current.maxValue !== initial.maxValue
        );
      },
      valueFormatter: formatWon,
      step: 5000,
      enable: false,
      enableMin: true,
      enableMax: true,
      minValue: 50_000,
      maxValue: 100_000,
      min: 0,
      max: 200_000,
      type: "slider",
    },
    netLeasableArea: {
      key: "netLeasableArea",
      label: "전용면적",
      dirtyChecker: (current, initial) => {
        if (!initial) return;

        return (
          current.enable !== initial.enable ||
          current.enableMin !== initial.enableMin ||
          current.enableMax !== initial.enableMax ||
          current.maxIndex !== initial.maxIndex ||
          current.minIndex !== initial.minIndex
        );
      },
      valueFormatter: (v) => `${v}㎡`,
      enable: false,
      options: NET_LEASABLE_AREA_OPTION,
      enableMin: false,
      enableMax: false,
      minIndex: 0,
      maxIndex: 2,
      type: "discrete-slider",
    },
  },
  lease: {
    deposit: {
      key: "deposit",
      label: "보증금",
      dirtyChecker: (current, initial) => {
        if (!initial) return;

        return (
          current.enable !== initial.enable ||
          current.enableMin !== initial.enableMin ||
          current.enableMax !== initial.enableMax ||
          current.minValue !== initial.minValue ||
          current.maxValue !== initial.maxValue
        );
      },
      valueFormatter: formatWon,
      step: 1000,
      enable: false,
      enableMin: true,
      enableMax: true,
      minValue: 10_000,
      maxValue: 50_000,
      min: 0,
      max: 120_000,
      type: "slider",
    },
    monthlyRent: {
      key: "monthlyRentFee",
      label: "월세",
      dirtyChecker: (current, initial) => {
        if (!initial) return;

        return (
          current.enable !== initial.enable ||
          current.enableMin !== initial.enableMin ||
          current.enableMax !== initial.enableMax ||
          current.minValue !== initial.minValue ||
          current.maxValue !== initial.maxValue
        );
      },
      valueFormatter: formatWon,
      step: 50,
      enable: false,
      enableMin: true,
      enableMax: true,
      minValue: 20,
      maxValue: 100,
      min: 0,
      max: 500,
      type: "slider",
    },
    netLeasableArea: {
      key: "netLeasableArea",
      label: "전용면적",
      dirtyChecker: (current, initial) => {
        if (!initial) return;

        return (
          current.enable !== initial.enable ||
          current.enableMin !== initial.enableMin ||
          current.enableMax !== initial.enableMax ||
          current.maxIndex !== initial.maxIndex ||
          current.minIndex !== initial.minIndex
        );
      },
      valueFormatter: (v) => `${v}㎡`,
      enable: false,
      options: NET_LEASABLE_AREA_OPTION,
      enableMin: false,
      enableMax: false,
      minIndex: 0,
      maxIndex: 2,
      type: "discrete-slider",
    },
  },
});

export const buildFilterParam = (filters) => {
  if (!filters) return {};

  return Object.values(filters)
    .filter((f) => f.enable)
    .reduce((acc, cur, index, arr) => {
      if (cur.type === "slider") {
        if (cur.enableMin) {
          acc[toMinFilterParam(cur.key)] = cur.minValue;
        }
        if (cur.enableMax) {
          acc[toMaxFilterParam(cur.key)] = cur.maxValue;
        }
      } else if (cur.type === "discrete-slider") {
        if (cur.enableMin) {
          acc[toMinFilterParam(cur.key)] = cur.options[cur.minIndex];
        }
        if (cur.enableMax) {
          acc[toMaxFilterParam(cur.key)] = cur.options[cur.maxIndex];
        }
      }

      return acc;
    }, {});
};
