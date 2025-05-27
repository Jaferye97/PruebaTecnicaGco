import { Form, Spinner, Button, Row } from "react-bootstrap";

import { useCreateProduct } from "./useCreateProduct";

const CreateProduct = () => {
  const { state, stateUpdaters } = useCreateProduct();
  const { loadingCreate, inputs } = state;

  const { handleChange, createProduct, handleBack } = stateUpdaters;

  return (
    <>
      <div className="d-flex justify-content-start mb-3">
        <Button
          variant="danger"
          size="sm"
          onClick={handleBack}
        >
          ← Back
        </Button>
      </div>

      <Row className="mt-3">
        <h3 className="text-center">Create Product</h3>
      </Row>

      <Form
        onSubmit={createProduct}
        className="p-4 border rounded shadow-sm"
      >
        <Form.Group className="mb-3">
          <Form.Label>Name</Form.Label>
          <Form.Control
            type="text"
            name="name"
            value={inputs?.name || ""}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Description</Form.Label>
          <Form.Control
            as="textarea"
            rows={3}
            name="description"
            value={inputs?.description || ""}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Category</Form.Label>
          <Form.Control
            type="text"
            name="category"
            value={inputs?.category || ""}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Code</Form.Label>
          <Form.Control
            type="text"
            name="code"
            value={inputs?.code || ""}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Price</Form.Label>
          <Form.Control
            type="number"
            step="0.01"
            name="price"
            value={inputs?.price ?? ""}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Stock</Form.Label>
          <Form.Control
            type="number"
            name="stock"
            value={inputs?.stock ?? ""}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Button
          variant="primary"
          type="submit"
          disabled={loadingCreate}
        >
          {loadingCreate ? (
            <Spinner
              animation="border"
              size="sm"
            />
          ) : (
            "Create Product"
          )}
        </Button>
      </Form>
    </>
  );
};

export default CreateProduct;
