import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Search from './pages/Search';
import SignUp from './pages/SignUp';
import SignIn from './pages/SignIn';
import ProfilePage from "./pages/ProfilePage";
import UploadWork from "./pages/UploadWork";
import CustomerProfile from "./pages/CustomerProfile";
import CustomerScreen from "./pages/CustomerScreen";
import Liked from "./pages/Liked";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/signin" element={<SignIn />} />
                <Route path="/profile/:username" element={<ProfilePage/>} />
                <Route path="/customer/profile/:username" element={<CustomerProfile/>} />
                <Route path="/upload/:username" element={<UploadWork />} />
                <Route path="/search" element={<Search/>} />
                <Route path="/liked" element={<Liked/>} />
                <Route path="/customer/screen" element={<CustomerScreen />} />
            </Routes>
        </Router>
    );
}

export default App;
