import Swal from "sweetalert2";
import type { ErrorResponseApi } from "../interfaces/responseApiInterfaces";

const messageError = "An incident has occurred, please contact the administrator. Thank you.";

export const requestData = async <T>(
  method: string,
  url: string,
  data?: object,
  baseURL: string = import.meta.env.VITE_API_URL_BASE
) => {
  try {
    const detailRequest = {
      method: method,
      headers: {
        "Content-Type": "application/json",
      },
      body: data ? JSON.stringify(data) : undefined,
    };

    const response = await fetch(baseURL + url, detailRequest);

    if (!response.ok) {
      const errorApi: ErrorResponseApi = await response.json();

      if (errorApi.error == "Internal Server Error") {
        Swal.fire({
          icon: "warning",
          title: errorApi.message || messageError,
          showConfirmButton: false,
          timer: 1500,
        });
      }

      if (errorApi.error == "Validation Error") {
        console.log(errorApi);
        Swal.fire({
          icon: "warning",
          title: "Validation Error",
          showConfirmButton: false,
          timer: 1500,
        });
      }

      return null;
    }

    const responseData: T = await response.json();

    return responseData;
  } catch (error) {
    Swal.fire({
      position: "top-end",
      icon: "warning",
      title: messageError,
      showConfirmButton: false,
      timer: 1500,
    });
    console.error(new Error(`Error fetching data: ${error}`));
    return null;
  }
};
