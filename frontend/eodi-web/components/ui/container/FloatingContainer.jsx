import { AnimatePresence } from "framer-motion";
import FloatingCard from "@/components/ui/container/FloatingCard";
import FloatingButton from "@/components/ui/FloatingButton";

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
  const horizontalClass = position === "left" ? "left-4 sm:left-6" : "right-4 sm:right-6";

  return (
    <div
      className={`fixed bottom-24 z-40 sm:bottom-6 md:top-[calc(4rem+8rem)] md:bottom-auto ${horizontalClass}`}
    >
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
