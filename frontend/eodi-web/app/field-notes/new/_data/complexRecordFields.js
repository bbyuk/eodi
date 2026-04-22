import {
  FACING_OPTIONS,
  STAR_SCORE_LABELS,
} from "@/app/field-notes/new/_data/fieldNoteOptions";
import COPY from "@/app/field-notes/new/_data/complexRecordCopy";

export const BASIC_RECORD_FIELDS = [
  {
    key: "complexMood",
    type: "starRating",
    title: { main: COPY.complexMoodLabel },
    defaultValue: null,
    props: {
      scoreLabels: STAR_SCORE_LABELS.complexMood,
    },
    isCompleted: (value) => Boolean(value),
  },
  {
    key: "surroundings",
    type: "starRating",
    title: { main: COPY.surroundingsLabel },
    defaultValue: null,
    props: {
      scoreLabels: STAR_SCORE_LABELS.surroundings,
    },
    isCompleted: (value) => Boolean(value),
  },
  {
    key: "parking",
    type: "starRating",
    title: { main: COPY.parkingLabel },
    defaultValue: null,
    props: {
      scoreLabels: STAR_SCORE_LABELS.parking,
    },
    isCompleted: (value) => Boolean(value),
  },
  {
    key: "commonMemo",
    type: "textArea",
    title: { main: COPY.commonMemoLabel },
    defaultValue: "",
    props: {
      placeholder: COPY.commonMemoPlaceholder,
      showCount: false,
    },
    isCompleted: (value) => value.trim().length > 0,
  },
];

export const VISITED_HOME_FIELDS = [
  [
    {
      key: "building",
      type: "number",
      title: { main: COPY.buildingLabel },
      defaultValue: "",
      props: {
        placeholder: COPY.buildingPlaceholder,
        maxValue: 9999,
      },
    },
    {
      key: "unit",
      type: "number",
      title: { main: COPY.unitLabel },
      defaultValue: "",
      props: {
        placeholder: COPY.unitPlaceholder,
        maxValue: 99999,
      },
    },
  ],
  {
    key: "askingPrice",
    type: "askingPrice",
    title: { main: COPY.priceLabel },
    defaultValue: "",
    props: {
      placeholder: COPY.pricePlaceholder,
    },
  },
  {
    key: "facing",
    type: "facing",
    title: { main: COPY.facingLabel },
    defaultValue: "",
    props: {
      options: FACING_OPTIONS,
    },
  },
  {
    key: "floor",
    type: "number",
    title: { main: COPY.floorLabel },
    defaultValue: "",
    props: {
      placeholder: COPY.floorPlaceholder,
      maxValue: 999,
    },
  },
  {
    key: "memo",
    type: "textArea",
    title: { main: COPY.homeMemoLabel },
    defaultValue: "",
    props: {
      placeholder: COPY.homeMemoPlaceholder,
      showCount: false,
    },
  },
  {
    key: "isHighlighted",
    type: "highlightToggle",
    title: {
      main: COPY.highlightHomeLabel,
      sub: COPY.highlightHomeDescription,
    },
    defaultValue: false,
  },
];
