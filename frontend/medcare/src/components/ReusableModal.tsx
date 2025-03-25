import React from "react";
import { Modal } from "react-bootstrap";

type ModalProps = {
    show: boolean;
    title: string;
    onClose: () => void;
    body: React.ReactNode;
}

const ReusableModal: React.FC<ModalProps> = ({ show, title, onClose, body }:ModalProps) => {
    return (
        <Modal  className="fade" show={show} onHide={onClose} centered scrollable>
            <Modal.Header closeButton>
                <Modal.Title>{title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>{body}</Modal.Body>
        </Modal>
    );
};

export default ReusableModal;