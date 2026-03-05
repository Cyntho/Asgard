import Header from "./components/layout/Header"
import Footer from "./components/layout/Footer"
import { Outlet } from "react-router-dom";
import { useNavigation } from "react-router-dom";


function App() {
  const navigation = useNavigation();  
  return (
    <>
      <Header />
      {
        navigation.state === "loading" ? (
          <div className="flex items-center justify-center min-h-245">
            <span className="text-4xl text-primary dark:text-lighter font-semibold">
              Loading...
            </span>
          </div>
        ) : (
          <div className="min-h-245">
            <Outlet />
          </div>
        )
      }      
      <Footer />
    </>
  );
}

export default App;
