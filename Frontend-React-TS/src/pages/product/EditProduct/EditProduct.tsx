import { Form, Spinner, Button, Row } from "react-bootstrap";

import { useEditProduct } from "./useEditProduct";
import { DotLoader } from "react-spinners";

const EditProduct = () => {
  const { state, stateUpdaters } = useEditProduct();
  const { loadingPage, loadingUpdate, inputs } = state;

  const { handleChange, editProduct, handleBack } = stateUpdaters;

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
        <h3 className="text-center">Edit Product</h3>
      </Row>
      {loadingPage ? (
        <div className="mt-5 d-flex justify-content-center">
          <DotLoader
            color="#0d6efd"
            size={100}
          />
        </div>
      ) : (
        <Form
          onSubmit={editProduct}
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

          <Form.Group className="mb-3">
            <Form.Check
              type="checkbox"
              name="active"
              label="Active"
              checked={inputs?.active ?? false}
              onChange={handleChange}
            />
          </Form.Group>

          <Button
            variant="primary"
            type="submit"
            disabled={loadingUpdate}
          >
            {loadingUpdate ? (
              <Spinner
                animation="border"
                size="sm"
              />
            ) : (
              "Update Product"
            )}
          </Button>
        </Form>
      )}
    </>
  );
};

export default EditProduct;
