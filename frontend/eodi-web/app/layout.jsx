import "./globals.css";
import Navbar from "@/components/layout/NavBar";

export const metadata = {
  title: "Lorem Ipsum",
  description: "Lorem ipsum dolor sit amet",
};

export default function RootLayout({ children }) {
  return (
    <html lang="ko">
      <body className="flex flex-col min-h-screen">
        <Navbar />
        <main className="flex flex-1 items-center justify-center">{children}</main>
      </body>
    </html>
  );
}
