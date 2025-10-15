"use client";

import { useState } from "react";
import Input from "@/components/Input";
import Select from "@/components/Select";
import Button from "@/components/Button";

export default function HomePage() {
  const [form, setForm] = useState({
    cash: "",
    region: "",
    housing: "",
  });

  const handleChange = (key, value) => setForm({ ...form, [key]: value });

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("입력값:", form);
  };

  return (
    <div className="w-full max-w-md p-8 rounded-2xl shadow-xl bg-white/70 backdrop-blur-md border border-white/50">
      <h2 className="text-2xl font-semibold text-center mb-6">내 상황을 입력해주세요</h2>

      <form className="space-y-5" onSubmit={handleSubmit}>
        <Input
          label="보유 현금 (만원)"
          type="number"
          value={form.cash}
          onChange={(e) => handleChange("cash", e.target.value)}
          placeholder="예: 80000"
        />

        <Input
          label="희망 지역"
          type="text"
          value={form.region}
          onChange={(e) => handleChange("region", e.target.value)}
          placeholder="예: 서울특별시 강남구"
        />

        <Select
          label="거주 형태"
          value={form.housing}
          onChange={(e) => handleChange("housing", e.target.value)}
          options={[
            { value: "", label: "선택해주세요" },
            { value: "APT", label: "아파트" },
            { value: "VILLA", label: "빌라 / 다세대" },
            { value: "OFFICETEL", label: "오피스텔" },
            { value: "HOUSE", label: "단독 / 다가구" },
          ]}
        />

        <Button type="submit" label="결과 확인하기" />
      </form>
    </div>
  );
}
