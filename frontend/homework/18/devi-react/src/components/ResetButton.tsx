type ButtonGroupProps = {
onReset: () => void;
};

const ResetButton = ({ onReset }: ButtonGroupProps) => {
return (
<div className="reset-button">
<button onClick={onReset}>Reset Filters</button>
</div>
);
};

export default ResetButton;