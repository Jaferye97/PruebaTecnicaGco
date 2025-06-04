import { Container, Row, Table, Button, Form, Dropdown } from "react-bootstrap";
import { DotLoader } from "react-spinners";
import { useNavigate } from "react-router-dom";
import { FaEllipsisV, FaEdit, FaPowerOff } from "react-icons/fa";

import { useProducts } from "./useProducts";

const Products = () => {
  const navigate = useNavigate();

  const { state, stateUpdaters } = useProducts();
  const { loading, data, inputs } = state;
  const { handleChange, submitForm, handleToggle } = stateUpdaters;

  const { option, text } = inputs;

  return (
    <Container>
      <Row className="mt-3">
        <h3 className="text-center">Products</h3>
      </Row>

      <div className="p-3 border rounded shadow-sm">
        <Form onSubmit={submitForm}>
          <Form.Label>Search option</Form.Label>
          <div className="d-flex gap-3 mb-3">
            <Form.Check
              type="radio"
              id="option"
              label="Code"
              name="option"
              value="Code"
              checked={option === "Code"}
              onChange={(e) => handleChange(e)}
            />
            <Form.Check
              type="radio"
              id="option"
              label="Category"
              name="option"
              value="Category"
              checked={option === "Category"}
              onChange={(e) => handleChange(e)}
            />
            <Form.Check
              type="radio"
              id="option"
              label="Name"
              name="option"
              value="Name"
              checked={option === "Name"}
              onChange={(e) => handleChange(e)}
            />
          </div>
          <div className="mb-3">
            <label
              htmlFor="textInput"
              className="form-label"
            >
              Enter text
            </label>
            <input
              id="textInput"
              type="text"
              className="form-control"
              placeholder="Text here..."
              value={text}
              name="text"
              onChange={(e) => handleChange(e)}
            />
          </div>

          <Button
            variant="primary"
            type="submit"
          >
            Search
          </Button>
        </Form>
      </div>

      <div className="mt-4 d-flex justify-content-end mb-3">
        <Button
          variant="success"
          size="sm"
          onClick={() => navigate("/product/create")}
        >
          New Product
        </Button>
      </div>

      {loading ? (
        <div className="mt-5 d-flex justify-content-center">
          <DotLoader
            color="#0d6efd"
            size={100}
          />
        </div>
      ) : (
        <div className="pb-4">
          <Table
            className="table-striped table-bordered text-nowrap shadow-sm rounded"
            responsive="md"
          >
            <thead className="table-dark">
              <tr>
                <th className="text-start">Code</th>
                <th className="text-start">Name</th>
                <th className="text-start">Category</th>
                <th className="text-center">Stock</th>
                <th className="text-center">Price</th>
                <th className="text-start">Description</th>
                <th className="text-center">State</th>
                <th className="text-center">Action</th>
              </tr>
            </thead>
            <tbody>
              {data?.map((item) => (
                <tr key={item.id}>
                  <td className="text-start">{item.code}</td>
                  <td className="text-start">{item.name}</td>
                  <td className="text-start">{item.category}</td>
                  <td className="text-center">{item.stock}</td>
                  <td className="text-center">${item.price?.toFixed(2)}</td>
                  <td className="text-start">{item.description}</td>
                  <td className="text-center">{item.active ? "Active" : "Inactive"}</td>
                  <td>
                    <Dropdown align="end">
                      <Dropdown.Toggle
                        variant="light"
                        size="sm"
                        className="border-0"
                      >
                        <FaEllipsisV />
                      </Dropdown.Toggle>

                      <Dropdown.Menu>
                        <Dropdown.Item onClick={() => navigate(`/product/${item.id}`)}>
                          <FaEdit className="me-2" />
                          Edit Product
                        </Dropdown.Item>
                        <Dropdown.Item onClick={() => handleToggle(item.id || 0)}>
                          <FaPowerOff
                            className={`me-2 ${item.active ? "text-danger" : "text-success"}`}
                          />
                          {item.active ? "Deactivate" : "Activate"}
                        </Dropdown.Item>
                      </Dropdown.Menu>
                    </Dropdown>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      )}
    </Container>
  );
};

export default Products;
