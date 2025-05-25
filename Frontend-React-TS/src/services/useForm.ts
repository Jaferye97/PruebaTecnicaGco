import { useState } from "react";

export const useForm = <T>(initialState: T) => {
  const [inputs, setInputs] = useState<T>(initialState);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, checked, type } = event.target;
    setInputs((old) => ({
      ...old,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleChangeData = (data: T) => {
    setInputs((old) => ({
      ...old,
      ...data,
    }));
  };

  const handleChangeDate = (name: string, date: Date) => {
    setInputs((old) => ({
      ...old,
      [name]: date,
    }));
  };

  const handleChangeSelect = (name: string, value: string) => {
    setInputs((old) => ({
      ...old,
      [name]: value,
    }));
  };

  const reset = () => {
    setInputs(initialState);
  };

  return {
    inputs,
    handleChange,
    handleChangeDate,
    handleChangeSelect,
    handleChangeData,
    reset,
  };
};
