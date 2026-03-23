import ServicePlaceholderPage from "@/app/_components/ServicePlaceholderPage";
import { servicesBySlug } from "@/app/_data/services";

export const metadata = {
  title: "임장노트 | 어디살까",
  description: "공유 가능한 임장노트 서비스 준비 페이지",
};

export default function FieldNotesPage() {
  return <ServicePlaceholderPage service={servicesBySlug["field-notes"]} />;
}

