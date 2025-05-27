//Libraries
import { useState } from "react";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom";

//Services
import { requestData } from "../../../services/axionApi";
import { useForm } from "../../../services/useForm";

//Interfaces
import Product from "../interfaces/ProductInterfaces";

export const useCreateProduct = () => {
  const navigate = useNavigate();

  const [loadingCreate, setLoadingCreate] = useState<boolean>(false);

  const { inputs, handleChange } = useForm<Product>({ active: true });

  const createProduct = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      setLoadingCreate(true);

      const result = await requestData<Product>("post", "/product", inputs);

      if (result != null) {
        navigate(`/product`);

        Swal.fire({
          icon: "success",
          title: "Good",
          text: "Product created successfully.",
          showConfirmButton: false,
          timer: 1500,
        });
      }
    } catch (error) {
      console.error("Error calling API:", error);
    } finally {
      setLoadingCreate(false);
    }
  };

  const handleBack = () => {
    navigate(`/product`);
  };

  const stateUpdaters = { handleChange, createProduct, handleBack };

  const state = {
    loadingCreate,
    inputs,
  };

  return { state, stateUpdaters };
};
