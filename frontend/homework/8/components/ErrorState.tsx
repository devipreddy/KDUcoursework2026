type ErrorStateProps = {
  message: string;
};

const ErrorState: React.FC<ErrorStateProps> = ({ message }) => {
  return (
    <div className="error-state">
      <p>{message}</p>
    </div>
  );
};

export default ErrorState;
