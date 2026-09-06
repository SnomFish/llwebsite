import "./App.css"
import LoomianContainer from "./components/LoomianContainer.jsx"

function App() {

  return (
    <>
      <div className="headerContainer">
        <div className="headerLeft">
          <h1>Loomain Legacy Branching Battle</h1>
        </div>
        <div className="headerRight">
          
        </div>
      </div> 

      <div className="interfaceContainer">
        <div className="user sideContainer">
            
          <div className="user partyContainer">
            <LoomianContainer />
            <LoomianContainer />
            <LoomianContainer />
            <LoomianContainer />
            <LoomianContainer />
            <LoomianContainer />
            <LoomianContainer />
          </div>

        </div>

        <div className="fieldContainer">

        </div>

        <div className="enemy sideContainer">

          <div className="enemy partyContainer">
            <LoomianContainer />
            <LoomianContainer />
            <LoomianContainer />
            <LoomianContainer />
            <LoomianContainer />
            <LoomianContainer />
            <LoomianContainer />
          </div>

        </div>
      </div>
    </>
  )
}

export default App
