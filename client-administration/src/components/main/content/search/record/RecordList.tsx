import * as React from 'react';
import RecordListItem from './RecordListItem';

interface RecordListProps {
    
}
 
class RecordList extends React.Component<RecordListProps> {
    state = { }
    render() { 
        return ( 
            <div>
                <RecordListItem />
                <RecordListItem />
                <RecordListItem />
            </div>
         );
    }
}
 
export default RecordList;