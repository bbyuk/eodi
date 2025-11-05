import { AnimatePresence } from "framer-motion";
import FloatingCard from "@/components/ui/container/floating/FloatingCard";
import FloatingButton from "@/components/ui/container/floating/FloatingButton";

export default function FloatingContainer({
  isOpen,
  open,
  close,
  children,
  buttonIcon,
  buttonLabel,
  cardIcon,
  cardLabel,
}) {
  return (
    <div className="fixed right-6 top-[calc(4rem + 12vh)] z-40 md:right-6 sm:right-3">
      <AnimatePresence mode="wait">
        {isOpen ? (
          <FloatingButton label={buttonLabel} icon={buttonIcon} close={close} />
        ) : (
          <FloatingCard label={cardLabel} icon={cardIcon} open={open}>
            {children}
          </FloatingCard>
        )}
      </AnimatePresence>
    </div>
  );
}
