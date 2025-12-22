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
}) {
  return (
    <div className="fixed right-6 top-[calc(4rem + 12vh)] z-40 md:right-6 sm:right-3">
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
