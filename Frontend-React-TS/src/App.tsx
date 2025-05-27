import "./App.css";
import Layout from "./components/layout/Layout";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Products from "./pages/product/index/Products";
import EditProduct from "./pages/product/editProduct/EditProduct";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route
            path="/"
            element={<h1>Welcome</h1>}
          />
          <Route
            path="/Product"
            element={<Products />}
          />
          <Route
            path="/Product/:id"
            element={<EditProduct />}
          />
          <Route
            path="/Product/Movement"
            element={<h1>Movement Product</h1>}
          />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
