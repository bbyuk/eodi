import "./globals.css";
import Navbar from "@/components/layout/NavBar";
import ToastProvider from "@/components/ui/container/ToastProvider";

export const metadata = {
  title: "어디살까",
  description: "부동산 실거래가 기반 추천 서비스",
};

export default function RootLayout({ children }) {
  return (
    <html lang="ko">
      <body className="flex flex-col min-h-screen">
        <ToastProvider>
          <Navbar />
          <main className="flex-1 w-full">{children}</main>
        </ToastProvider>
      </body>
    </html>
  );
}
