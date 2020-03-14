import React from 'react';
import { useTranslation } from 'react-i18next';
import styled from 'styled-components';
import Typography from '@material-ui/core/Typography';

const Wrapper = styled.div`
  padding-top: 40px;
  text-align: center;
  width: 100%;
`;

function NotFound() {
  const { t } = useTranslation();
  return (
    <Wrapper>
      <Typography variant="h3">{t('NOT_FOUND.404')}</Typography>
      <Typography variant="h6">{t('NOT_FOUND.MSG')}</Typography>
    </Wrapper>
  );
}

export default NotFound;
