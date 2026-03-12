import { useCallback, useState } from "react";
import { api } from "@/lib/apiClient";

export function useDealMetadataQuery() {
  const [metadata, setMetadata] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const fetchMetadata = useCallback(async () => {
    setIsLoading(true);

    try {
      const res = await api.get("/real-estate/recommendation/metadata");

      setMetadata(res);
      return res;
    } finally {
      setIsLoading(false);
    }
  }, []);

  return {
    metadata,
    fetchMetadata,
    isLoading,
  };
}
