import "./LoomianContainer.css"

function LoomianContainer() {
	return (
	<>
		<div className="main container border">
			<div className="textInfo container">
				<div className="textInfo left">
					<div className="name">
						name
					</div>
					<div className="status border">
						psn
					</div>
				</div>
				<div className="textInfo right">
					<div className="ability">
						ability
					</div>
					<div className="item">
						item
					</div>
				</div>
			</div>
			<div className="statsAndMoves container">
				<div className="stats container">
					<div className="stat border"></div>
					<div className="stat border"></div>
					<div className="stat border"></div>
					<div className="stat border"></div>
					<div className="stat border"></div>
					<div className="stat border"></div>
					<div className="stat border"></div>
				</div>
				<div className="moves container">
					<div className="move border"></div>
					<div className="move border"></div>
					<div className="move border"></div>
					<div className="move border"></div>
				</div>
			</div>
		</div>
	</>);
}

export default LoomianContainer