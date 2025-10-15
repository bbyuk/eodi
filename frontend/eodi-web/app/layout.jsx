import "@/globals.css";

export default function RootLayout({ children }) {
  return (
    <html lang="ko">
      <body className="bg-gradient-to-br from-slate-100 to-slate-300 min-h-screen font-sans antialiased">
        <div className="flex flex-col min-h-screen">
          <header className="px-8 py-6 flex items-center justify-between backdrop-blur-md bg-white/30 border-b border-white/40 shadow-sm">
            <h1 className="text-2xl font-semibold tracking-tight text-gray-800">
              어디살까<span className="text-blue-600">?</span>
            </h1>
          </header>
          <main className="flex-1 flex justify-center items-center">{children}</main>
          <footer className="text-center py-6 text-sm text-gray-500">
            © 2025 어디살까 — Real Estate Lifestyle Assistant
          </footer>
        </div>
      </body>
    </html>
  );
}
