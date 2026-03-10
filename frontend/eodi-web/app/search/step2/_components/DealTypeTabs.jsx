"use client";

import CategoryButton from "@/components/ui/input/CategoryButton";

/**
 * DealTypeTabs
 *
 * props
 * - tabs
 * - selectedTab
 * - onChangeTab
 */

export default function DealTypeTabs({ tabs, selectedTab, onChangeTab }) {
  return (
    <>
      {tabs.map((tab) => (
        <CategoryButton
          key={tab.code}
          icon={tab.icon}
          onClick={() => onChangeTab(tab.code)}
          isActive={selectedTab === tab.code}
          label={tab.displayName}
        />
      ))}
    </>
  );
}
