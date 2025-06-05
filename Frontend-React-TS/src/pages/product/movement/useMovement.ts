//Libraries
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

//Services
import { requestData } from "../../../services/axionApi";

//Interfaces
import type { MovementsProduct } from "../interfaces/ProductInterfaces";

export const useMovement = () => {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();

  const [loadingPage, setLoadingPage] = useState<boolean>(true);
  const [data, setData] = useState<MovementsProduct>({ product: undefined, movements: undefined });

  useEffect(() => {
    getData(Number(id));
  }, []);

  const getData = async (id: number) => {
    try {
      const result = await requestData<MovementsProduct>("get", `/product/${id}/movements`);

      console.log(result);
      if (result != null) {
        setData(result);
      } else {
        navigate(`/product`);
      }
    } finally {
      setLoadingPage(false);
    }
  };

  const handleBack = () => {
    navigate(`/product`);
  };

  const stateUpdaters = { handleBack };

  const state = {
    loadingPage,
    data,
  };

  return { state, stateUpdaters };
};
