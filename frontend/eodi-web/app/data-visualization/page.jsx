import ServicePlaceholderPage from "@/app/_components/ServicePlaceholderPage";
import { servicesBySlug } from "@/app/_data/services";

export const metadata = {
  title: "데이터 시각화 | 어디살까",
  description: "부동산 데이터 시각화 서비스 준비 페이지",
};

export default function DataVisualizationPage() {
  return <ServicePlaceholderPage service={servicesBySlug["data-visualization"]} />;
}
