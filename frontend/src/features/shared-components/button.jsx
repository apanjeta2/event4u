import React from 'react';
import styled from 'styled-components';
import MaterialButton from '@material-ui/core/Button';

const Wrapper = styled(MaterialButton)`
  background-color: ${props => props.theme.buttonColor};

  &:hover {
    background-color: ${props => props.theme.buttonColor};
    opacity: 0.7;
  }
`;

function Button(props) {
  return <Wrapper {...props} />;
}

export default Button;
