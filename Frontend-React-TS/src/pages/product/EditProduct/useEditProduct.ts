//Libraries
import { useEffect, useState } from "react";
import Swal from "sweetalert2";
import { useNavigate, useParams } from "react-router-dom";

//Services
import { requestData } from "../../../services/axionApi";
import { useForm } from "../../../services/useForm";

//Interfaces
import Product from "../interfaces/ProductInterfaces";

export const useEditProduct = () => {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();

  const [loadingPage, setLoadingPage] = useState<boolean>(true);
  const [loadingUpdate, setLoadingUpdate] = useState<boolean>(false);
  const [data, setData] = useState<Product>();

  const { inputs, setInputs, handleChange } = useForm<Product>({});

  useEffect(() => {
    getData(Number(id));
  }, []);

  const getData = async (id: number) => {
    try {
      const result = await requestData<Product>("get", `/product/${id}`);

      if (result != null) {
        setData(result);
        setInputs(result);
      } else {
        navigate(`/product`);
      }
    } finally {
      setLoadingPage(false);
    }
  };

  const editProduct = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      setLoadingUpdate(true);

      const result = await requestData<Product>("put", "/product/update", inputs);

      if (result != null) {
        setData(result);
        setInputs(result);

        Swal.fire({
          icon: "success",
          title: "Good",
          text: "Product updated successfully.",
          showConfirmButton: false,
          timer: 1500,
        });
      }
    } catch (error) {
      console.error("Error calling API:", error);
    } finally {
      setLoadingUpdate(false);
    }
  };

  const handleBack = () => {
    navigate(`/product`);
  };

  const stateUpdaters = { handleChange, editProduct, handleBack };

  const state = {
    loadingPage,
    loadingUpdate,
    data,
    inputs,
  };

  return { state, stateUpdaters };
};
