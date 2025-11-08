import { useEffect, useRef } from "react";

export function useInfiniteScroll(loadMore, hasMore) {
  const observerRef = useRef();

  useEffect(() => {
    if (!hasMore) return;
    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting) loadMore();
      },
      { threshold: 1.0 }
    );

    if (observerRef.current) observer.observe(observerRef.current);

    return () => observer.disconnect();
  }, [hasMore, loadMore]);

  return observerRef;
}
