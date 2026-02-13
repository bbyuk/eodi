import { AnimatePresence } from "framer-motion";
import FloatingCard from "@/components/ui/container/floating/FloatingCard";
import FloatingButton from "@/components/ui/container/floating/FloatingButton";
import { useEffect } from "react";

export default function FloatingContainer({
  isOpen = false,
  open,
  close,
  children,
  buttonIcon,
  buttonLabel,
  cardIcon,
  cardLabel,
  activeCount,
  position = "right",
}) {
  const horizontalClass =
    position === "left" ? "left-6 md:left-6 sm:left-3" : "right-6 md:right-6 sm:right-3";

  return (
    <div className={`fixed top-[calc(4rem + 12vh)] z-40 ${horizontalClass}`}>
      <AnimatePresence mode="wait">
        {isOpen ? (
          <FloatingCard label={cardLabel} icon={cardIcon} close={close}>
            {children}
          </FloatingCard>
        ) : (
          <FloatingButton
            label={buttonLabel}
            icon={buttonIcon}
            open={open}
            activeCount={activeCount}
          />
        )}
      </AnimatePresence>
    </div>
  );
}
