import "./globals.css";
import Navbar from "@/components/layout/Navbar";

export const metadata = {
  title: "Lorem Ipsum",
  description: "Lorem ipsum dolor sit amet",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body className="flex flex-col min-h-screen">
        <Navbar />
        <main className="flex-1">{children}</main>
      </body>
    </html>
  );
}
