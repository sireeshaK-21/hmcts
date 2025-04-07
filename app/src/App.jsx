import reactLogo from './assets/react.svg'
import React, { useEffect, useState } from "react";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";
import { Container, Row, Col, Alert } from "react-bootstrap";
import TaskForm from "./assets/components/TaskForm";
import TaskList from "./assets/components/TaskList";

const App = () => {
    const [tasks, setTasks] = useState([]);
    const [task, setTask] = useState({
      id: null,
      title: '',
      description: '',
      status: '',
      dueDate: ''
    });
    const [alert, setAlert] = useState(null);

    const apiUrl = 'http://localhost:8080';

    // 🔁 Fetch tasks on load
    useEffect(() => {
      fetchTasks();
    }, []);

    const fetchTasks = async () => {
      try {
        const res = await axios.get(apiUrl + '/');
        setTasks(res.data);
      } catch (error) {
        console.error('Error fetching tasks:', error);
        showAlert('Error fetching tasks', 'danger');
      }
    };

    const handleChange = (e) => {
      setTask({
        ...task,
        [e.target.name]: e.target.value
      });
    };

    const handleSubmit = async (e) => {
      e.preventDefault();
      try {
        if (task.id) {
          await axios.put(`${apiUrl}/${task.id}`, task);
          showAlert('Task updated successfully!', 'success');
        } else {
          await axios.post(apiUrl + '/', task);
          showAlert('Task created successfully!', 'success');
        }
        setTask({ id: null, title: '', description: '', status: '', dueDate: '' });
        fetchTasks();
      } catch (error) {
        console.error('Error saving task:', error);
        showAlert('Error saving task', 'danger');
      }
    };

    const handleEdit = (selectedTask) => {
      setTask(selectedTask);
    };

    const handleDelete = async (id) => {
      if (!window.confirm('Are you sure you want to delete this task?')) return;
      try {
        await axios.delete(`${apiUrl}/${id}`);
        fetchTasks();
        showAlert('Task deleted successfully!', 'info');
      } catch (error) {
        console.error('Error deleting task:', error);
        showAlert('Error deleting task', 'danger');
      }
    };

    const showAlert = (message, variant) => {
      setAlert({ message, variant });
      setTimeout(() => setAlert(null), 3000);
    };

    return (
      <Container className="py-4">
        <h1 className="text-center mb-4">Task Manager</h1>

        {alert && <Alert variant={alert.variant}>{alert.message}</Alert>}

        <Row>
          <Col md={6}>
            <TaskForm task={task} handleChange={handleChange} handleSubmit={handleSubmit} />
          </Col>
          <Col md={6}>
            <TaskList tasks={tasks} handleEdit={handleEdit} handleDelete={handleDelete} />
          </Col>
        </Row>
      </Container>
    );
};

export default App;