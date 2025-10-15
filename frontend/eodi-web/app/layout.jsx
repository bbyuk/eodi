import "@/globals.css";
import Header from "@/components/Header";

export default function RootLayout({ children }) {
  return (
    <html lang="ko">
      <body className="min-h-screen bg-gradient-to-br from-slate-100 to-slate-300 font-sans antialiased text-gray-800">
        <div className="flex flex-col min-h-screen">
          <Header />
          <main className="flex-1 flex justify-center items-center">{children}</main>
          <footer className="text-center py-6 text-sm text-gray-500">
            © 2025 어디살까 — Real Estate Lifestyle Assistant
          </footer>
        </div>
      </body>
    </html>
  );
}
