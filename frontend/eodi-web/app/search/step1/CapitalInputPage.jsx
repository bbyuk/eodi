"use client";

import CashInput from "@/components/ui/input/CashInput";
import PageHeader from "@/components/ui/header/PageHeader";
import { useEffect, useState } from "react";
import { useSearchStore } from "@/app/search/store/searchStore";
import { context } from "@/app/search/_const/context";
import { formatWon } from "@/app/search/_util/util";
import { useSearchContext } from "@/app/search/layout";
import { useController } from "@/app/search/step1/_hooks/useController";

export default function CapitalInputPage() {
  const controller = useController();

  return (
    <section className="w-full px-8 pt-[1vh] pb-[5vh] overflow-x-hidden">
      <PageHeader {...controller.page} />

      <CashInput
        label={"보유 예산 (만 원 단위)"}
        placeholder={"예: 50000"}
        onChange={controller.cash.onChange}
        onEnter={controller.cash.onEnter}
        value={controller.cash.value}
        unit={"만 원"}
        formatter={controller.cash.formatter}
      />
    </section>
  );
}
