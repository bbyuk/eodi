import AskingPriceField from "@/app/field-notes/new/_components/field/AskingPriceField";
import FacingField from "@/app/field-notes/new/_components/field/FacingField";
import HighlightToggleField from "@/app/field-notes/new/_components/field/HighlightToggleField";
import NumberInputField from "@/app/field-notes/new/_components/field/NumberInputField";
import StarRatingField from "@/app/field-notes/new/_components/field/StarRatingField";
import TextAreaField from "@/app/field-notes/new/_components/field/TextAreaField";

export default function FieldRenderer({ field, value, onChange }) {
  switch (field.type) {
    case "askingPrice":
      return (
        <AskingPriceField
          title={field.title}
          askingPrice={value}
          onChangeAskingPrice={onChange}
          {...field.props}
        />
      );
    case "facing":
      return (
        <FacingField title={field.title} value={value} onChange={onChange} {...field.props} />
      );
    case "highlightToggle":
      return <HighlightToggleField title={field.title} value={value} onChange={onChange} />;
    case "number":
      return (
        <NumberInputField title={field.title} value={value} onChange={onChange} {...field.props} />
      );
    case "starRating":
      return (
        <StarRatingField title={field.title} value={value} onChange={onChange} {...field.props} />
      );
    case "textArea":
      return (
        <TextAreaField title={field.title} value={value} onChange={onChange} {...field.props} />
      );
    default:
      return null;
  }
}
