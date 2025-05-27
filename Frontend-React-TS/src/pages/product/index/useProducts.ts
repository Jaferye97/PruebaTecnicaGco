//Libraries
import { useEffect, useState } from "react";
import Swal from "sweetalert2";

//Services
import { requestData } from "../../../services/axionApi";
import { useForm } from "../../../services/useForm";

//Interfaces
import Product from "../interfaces/ProductInterfaces";
import SearchOptionProduct from "../interfaces/SearchOptionProductInterface";

export const useProducts = () => {
  const [loading, setLoading] = useState<boolean>(true);
  const [data, setData] = useState<Product[]>();

  const { inputs, handleChange } = useForm<SearchOptionProduct>({
    option: "Code",
    text: "",
  });

  useEffect(() => {
    getData();
  }, []);

  const getData = async () => {
    const result = await requestData<Product[]>("get", "/product");

    if (result != null) {
      setData(result);
    }

    setLoading(false);
  };

  const getDataFilter = async () => {
    try {
      setLoading(true);

      const { option, text } = inputs;
      let url = "/product";

      if (text == "" || text == undefined) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Please enter text to search.",
        });
        return;
      }

      switch (option) {
        case "Code":
          url = `/product/findByCode/${text}`;
          break;
        case "Category":
          url = `/product/findByCategory/${text}`;
          break;
        case "Name":
          url = `/product/findByName/${text}`;
          break;
      }
      const result = await requestData<Product[]>("get", url);

      if (result != null) {
        setData(result);
      }
    } catch (error) {
      console.error("Error calling API:", error);
    } finally {
      setLoading(false);
    }
  };

  const submitForm = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    getDataFilter();
  };

  const handleToggle = async (id: number) => {
    try {
      setLoading(true);

      const result = await requestData<Product>("post", `/product/toggleIsActive/${id}`);

      if (result != null) {
        const { text } = inputs;

        if (!(text == "") && !(text == undefined)) {
          getDataFilter();
        } else {
          getData();
        }

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
      setLoading(false);
    }
  };

  const stateUpdaters = {
    handleChange,
    getDataFilter,
    handleToggle,
    submitForm,
  };

  const state = {
    loading,
    data,
    inputs,
  };

  return { state, stateUpdaters };
};
