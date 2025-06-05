import { Button, Row, Form, Table } from "react-bootstrap";
import { DotLoader } from "react-spinners";

import { useMovement } from "./useMovement";

const Movement = () => {
  const { state, stateUpdaters } = useMovement();
  const { loadingPage, data } = state;
  const { product, movements } = data;
  const { handleBack } = stateUpdaters;

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

      {loadingPage ? (
        <div className="mt-5 d-flex justify-content-center">
          <DotLoader
            color="#0d6efd"
            size={100}
          />
        </div>
      ) : (
        <>
          <Row className="mt-3">
            <h3 className="text-center">Product</h3>
          </Row>
          <Form className="p-4 border rounded shadow-sm">
            <Form.Group className="mb-3">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                name="name"
                value={
                  product?.name ? `${product?.name} ${product?.code} ${product?.category}` : ""
                }
                required
                disabled
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Description</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                name="description"
                value={
                  product?.description
                    ? `${product?.description}\nPrice: ${product?.price}\nStock: ${product?.stock}`
                    : ""
                }
                required
                disabled
              />
            </Form.Group>
          </Form>

          <Row className="mt-5">
            <h3 className="text-center">Movements Product</h3>
          </Row>
          <div className="pb-4">
            <Table
              className="table-striped table-bordered text-nowrap shadow-sm rounded"
              responsive="md"
            >
              <thead className="table-dark">
                <tr>
                  <th className="text-start">Date</th>
                  <th className="text-start">Type</th>
                  <th className="text-start">Amout</th>
                  <th className="text-center">Description</th>
                </tr>
              </thead>
              <tbody>
                {movements?.map((item) => (
                  <tr key={item.id}>
                    <td className="text-start">{item.date}</td>
                    <td className="text-start">{item.type}</td>
                    <td className="text-start">{item.amount}</td>
                    <td className="text-start">{item.description}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </div>
        </>
      )}
    </>
  );
};

export default Movement;
