import { GridColDef, GridValueGetterParams } from '@mui/x-data-grid'

export const columns: GridColDef[] = [
    { field: 'id', headerName: 'ID', width: 90 },
    {
        field: 'title',
        headerName: 'Title',
        width: 150,
        editable: true,
    },
    {
        field: 'views',
        headerName: 'Views',
        type: 'number',
        width: 110,
    },
    {
        field: 'likes',
        headerName: 'Likes',
        width: 100,
        type: 'number',
    },
    {
        field: 'dislikes',
        headerName: 'Dislikes',
        width: 100,
        type: 'number',
    },
    {
      field: 'description',
      headerName: 'Description',
      description: 'This column has a value getter and is not sortable.',
      sortable: false,
      width: 250,
      editable: true,
    },
];
  
export const rows = [
    { id: 1, title: 'Music', views: 1000, likes: 35, dislikes: 10, description: 'fkdshjdsfnjdjsfkjdshfkjdsfjk' },

];