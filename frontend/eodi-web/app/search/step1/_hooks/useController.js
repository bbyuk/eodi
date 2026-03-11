import { useSearchStore } from "@/app/search/store/searchStore";
import { useEffect } from "react";
import { context } from "@/app/search/_const/context";
import useViewModel from "@/app/search/step1/_hooks/useViewModel";
import { formatWon } from "@/app/search/_util/util";
import { useSearchContext } from "@/app/search/layout";

export function useController() {
  const { resetStep1, cash, setCash, setCurrentContext, currentContext } = useSearchStore();
  const { goNext } = useSearchContext();

  const {
    state: { withLoan },
    derived: { pageHeaderTitle, pageHeaderDescription },
    actions: { setWithLoan },
  } = useViewModel();

  const handleCashInputEnter = () => {
    goNext();
  };

  const handleCashInputChange = (value) => {
    setCash(value);
  };

  useEffect(() => {
    setCurrentContext(context.cash);
  }, [setCurrentContext]);

  useEffect(() => {
    resetStep1();
  }, []);

  return {
    page: {
      title: pageHeaderTitle,
      description: pageHeaderDescription,
    },
    cash: {
      value: cash,
      formatter: formatWon,
      onEnter: handleCashInputEnter,
      onChange: handleCashInputChange,
    },
  };
}
