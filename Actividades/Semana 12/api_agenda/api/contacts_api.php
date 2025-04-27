<?php
require_once '../controllers/contactsController.php';
require_once '../utils/error.php';

header('Content-Type: application/json');
error_reporting(0);
ini_set('display_errors', 0);

$contactsController = new ContactsController();
$method = $_SERVER['REQUEST_METHOD'];

function getJsonInput() {
    $input = file_get_contents('php://input');
    return json_decode($input, true);
}

switch ($method) {
    case 'GET':
        if (isset($_GET['id'])) {
            $id = $_GET['id'];
            $contactsController->getContactById($id);
        } elseif (isset($_GET['search'])) {
            $term = $_GET['search'];
            $contactsController->searchContacts($term);
        } else {
            $contactsController->getAllContacts();
        }
        break;

    case 'POST':
        $data = $_POST;
        if (empty($data)) {
            $data = getJsonInput();
        }
        
        if (!isset($data['contact_name']) || !isset($data['contact_phone'])) {
            CustomError::throwError("Nombre y teléfono son requeridos.", 400);
        }
        
        $name = $data['contact_name'];
        $phone = $data['contact_phone'];
        $contactsController->createContact($name, $phone);
        break;

    case 'PUT':
        $data = getJsonInput();
        if (!isset($data['id_contact']) || !isset($data['contact_name']) || !isset($data['contact_phone'])) {
            CustomError::throwError("ID, nombre y teléfono son requeridos para actualizar.", 400);
        }

        $id = $data['id_contact'];
        $name = $data['contact_name'];
        $phone = $data['contact_phone'];
        $contactsController->updateContact($id, $name, $phone);
        break;

    case 'DELETE':
        if (isset($_GET['id'])) {
            $id = $_GET['id'];
            $contactsController->deleteContact($id);
        } else {
            CustomError::throwError("ID de contacto es requerido para eliminar.", 400);
        }
        break;

    default:
        CustomError::throwError("Método inválido.", 405);
        break;
}
?>
